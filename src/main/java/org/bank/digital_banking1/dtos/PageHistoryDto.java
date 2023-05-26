package org.bank.digital_banking1.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PageHistoryDto {
private String id;
private int currentPage;
private int totalPages;
private int size;
private List<OperationDto> operationslist;
private double balance;
}
