package com.example.demo.model;

import java.time.LocalDateTime;

public record Note(int id, String title, String content, LocalDateTime createdAt){}

