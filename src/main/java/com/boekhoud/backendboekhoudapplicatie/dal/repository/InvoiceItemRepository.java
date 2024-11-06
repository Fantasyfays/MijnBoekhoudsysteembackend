package com.boekhoud.backendboekhoudapplicatie.dal.repository;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}
