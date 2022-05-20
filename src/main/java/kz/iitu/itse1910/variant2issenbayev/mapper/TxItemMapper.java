package kz.iitu.itse1910.variant2issenbayev.mapper;

import kz.iitu.itse1910.variant2issenbayev.dto.response.TxItemResp;
import kz.iitu.itse1910.variant2issenbayev.entity.TransactionItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TxItemMapper {
    TxItemMapper INSTANCE = Mappers.getMapper(TxItemMapper.class);

    @Mapping(target = "transactionId", source = "entity.transaction.id")
    @Mapping(target = "productId", source = "entity.product.id")
    @Mapping(target = "productName", source = "entity.product.name")
    @Mapping(target = "purchaseUom", source = "entity.uom.purchaseUom")
    TxItemResp toDto(TransactionItem entity);
}
