package com.moonlighthotel.hotelmanagementsystem.controller;

import com.moonlighthotel.hotelmanagementsystem.converter.CategoryConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.category.CategoryRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.category.CategoryResponse;
import com.moonlighthotel.hotelmanagementsystem.exception.model.RecordNotFoundErrorModel;
import com.moonlighthotel.hotelmanagementsystem.exception.model.ValidationFailErrorModel;
import com.moonlighthotel.hotelmanagementsystem.model.category.Category;
import com.moonlighthotel.hotelmanagementsystem.service.CategoryService;
import com.moonlighthotel.hotelmanagementsystem.swagger.SwaggerConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cars/categories")
@Tag(name = SwaggerConfiguration.TRANSFER_TAG)
public class CategoryController {

    @Autowired
    private final CategoryConverter categoryConverter;

    @Autowired
    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a new car category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<CategoryResponse> save(@RequestBody @Valid CategoryRequest categoryRequest) {
        Category category = categoryConverter.toCategory(categoryRequest);
        Category savedCategory = categoryService.save(category);
        CategoryResponse categoryResponse = categoryConverter.toCategoryResponse(savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update car category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<CategoryResponse> update(@Parameter(description = "Category ID", content = @Content(
            schema = @Schema(type = "integer", format = ""))) @PathVariable Long id,
                                                   @RequestBody @Valid CategoryRequest categoryRequest) {
        Category category = categoryConverter.toCategory(categoryRequest);
        Category updatedCategory = categoryService.update(id, category);
        CategoryResponse categoryResponse = categoryConverter.toCategoryResponse(updatedCategory);
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
    }
}