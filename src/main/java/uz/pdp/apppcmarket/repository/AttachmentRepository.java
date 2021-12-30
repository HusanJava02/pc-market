package uz.pdp.apppcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apppcmarket.entity.Attachment;
import uz.pdp.apppcmarket.entity.Product;

public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {

}
