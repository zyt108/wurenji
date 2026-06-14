package com.md.basePlatform.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.md.basePlatform.common.ApiResponse;
import com.md.basePlatform.common.PageResult;
import com.md.basePlatform.domain.Drone;
import com.md.basePlatform.domain.DroneForm;
import com.md.basePlatform.service.DroneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 无人机管理 REST API 控制器
 * <p>
 * 提供无人机的增删改查 RESTful API 接口，支持分页查询。
 * </p>
 */
@Tag(name = "无人机管理")
@RestController
@RequestMapping("/drones")
public class DroneController {

    /**
     * 无人机业务服务
     */
    private final DroneService droneService;

    /**
     * 构造函数，依赖注入
     *
     * @param droneService 无人机业务服务
     */
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    /**
     * 查询无人机列表（支持关键词搜索和分页）
     *
     * @param keyword  搜索关键词（可选）
     * @param status   状态筛选（0-停用，1-启用，可选）
     * @param pageNum  页码（默认1）
     * @param pageSize 每页大小（默认10）
     * @return 分页后的无人机列表响应
     */
    @Operation(summary = "无人机列表")
    @GetMapping("/api")
    public ApiResponse<PageResult<Drone>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Drone> list = droneService.list(keyword, status);
        PageInfo<Drone> pageInfo = new PageInfo<>(list);
        return ApiResponse.ok(PageResult.of(pageInfo));
    }

    /**
     * 查询单个无人机详情
     *
     * @param id 无人机ID
     * @return 无人机详情响应
     */
    @Operation(summary = "无人机详情")
    @GetMapping("/{id}")
    public ApiResponse<Drone> get(@PathVariable Long id) {
        return ApiResponse.ok(droneService.get(id));
    }

    /**
     * 新增无人机
     *
     * @param droneForm 表单数据
     * @return 创建后的无人机响应
     */
    @Operation(summary = "新增无人机")
    @PostMapping
    public ApiResponse<Drone> create(@Valid @RequestBody DroneForm droneForm) {
        return ApiResponse.ok(droneService.create(droneForm));
    }

    /**
     * 更新无人机信息
     *
     * @param id        无人机ID
     * @param droneForm 表单数据
     * @return 更新后的无人机响应
     */
    @Operation(summary = "修改无人机")
    @PostMapping("/{id}")
    public ApiResponse<Drone> update(@PathVariable Long id, @Valid @RequestBody DroneForm droneForm) {
        return ApiResponse.ok(droneService.update(id, droneForm));
    }

    /**
     * 删除无人机
     *
     * @param id 无人机ID
     * @return 删除结果响应
     */
    @Operation(summary = "删除无人机")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        droneService.delete(id);
        return ApiResponse.ok(null);
    }
}
