package com.boekhoud.backendboekhoudapplicatie.dal.repository;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
