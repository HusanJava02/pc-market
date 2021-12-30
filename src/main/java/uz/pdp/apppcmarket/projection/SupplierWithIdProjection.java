package uz.pdp.apppcmarket.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uz.pdp.apppcmarket.entity.Supplier;

@Projection(types = Supplier.class)
public interface SupplierWithIdProjection {
    @Value("#{target.id}")
    Integer getId();
}
