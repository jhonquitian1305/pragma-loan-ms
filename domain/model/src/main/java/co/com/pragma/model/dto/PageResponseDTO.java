package co.com.pragma.model.dto;

import java.util.List;

public record PageResponseDTO<T>(
        long totalElements,
        int totalPages,
        int page,
        int size,
        List<T> content
) {}