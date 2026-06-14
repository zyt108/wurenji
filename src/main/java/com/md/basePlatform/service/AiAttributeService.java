package com.md.basePlatform.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.basePlatform.domain.Drone;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * AI 属性生成服务。
 * <p>
 * 调用外部 AI 服务为无人机生成属性（标签、维护建议、风险等级）。
 * 支持降级策略，当 AI 服务不可用时使用默认建议。
 * </p>
 */
@Service
public class AiAttributeService {

    /** JSON 媒体类型 */
    private static final MediaType JSON = MediaType.parse("application/json");

    /** HTTP 客户端（20 秒超时） */
    private final OkHttpClient httpClient = new OkHttpClient.Builder()
            .callTimeout(20, TimeUnit.SECONDS)
            .build();

    /** JSON 解析器 */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /** AI 服务提供商 URL */
    @Value("${ai.provider.url}")
    private String providerUrl;

    /** AI 模型名称 */
    @Value("${ai.provider.model}")
    private String providerModel;

    /** AI 服务 API 密钥 */
    @Value("${ai.provider.api-key}")
    private String apiKey;

    /**
     * 为无人机丰富 AI 属性。
     * <p>
     * 调用外部 AI 服务生成无人机的标签、维护建议和风险等级。
     * 如果 API 密钥为空、请求失败或响应异常，则使用降级策略。
     * </p>
     *
     * @param drone 无人机实体
     */
    public void enrich(Drone drone) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            fallback(drone);
            return;
        }

        String prompt = "请根据无人机参数生成标签、维护建议、风险等级。返回JSON: tags, maintenance, risk。参数:"
                + drone.getModel() + "," + drone.getManufacturer() + ","
                + drone.getMaxPayload() + "," + drone.getMaxFlightTime();

        String payload = "{\"model\":\"" + providerModel + "\",\"messages\":[{\"role\":\"user\",\"content\":\""
                + prompt + "\"}]}";

        Request request = new Request.Builder()
                .url(providerUrl)
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(RequestBody.create(payload, JSON))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                fallback(drone);
                return;
            }

            JsonNode root = objectMapper.readTree(response.body().string());
            String content = root.path("choices").path(0).path("message").path("content").asText("");

            if (content.isEmpty()) {
                fallback(drone);
                return;
            }

            drone.setAiGenerated(1);
            if (drone.getRemark() == null || drone.getRemark().trim().isEmpty()) {
                drone.setRemark("AI建议:" + content);
            }
        } catch (IOException ex) {
            fallback(drone);
        }
    }

    /**
     * 降级策略：AI 服务不可用时设置默认维护建议。
     *
     * @param drone 无人机实体
     */
    private void fallback(Drone drone) {
        drone.setAiGenerated(1);
        if (drone.getRemark() == null || drone.getRemark().trim().isEmpty()) {
            drone.setRemark("AI降级建议: 每50小时检查电机与桨叶，电池循环200次后评估更换");
        }
    }
}
