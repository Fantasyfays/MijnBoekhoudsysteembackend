package com.boekhoud.backendboekhoudapplicatie.service.dalinterface;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Client;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import java.util.Optional;
import java.util.List;

public interface IInvoiceDal {
    Invoice save(Invoice invoice);
    Optional<Invoice> findById(Long id);
    List<Invoice> findAll();
    void deleteById(Long id);
    List<Invoice> findAllByClient(Client client);

}
