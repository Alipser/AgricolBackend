package agricol.backend.utiles.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import agricol.backend.dtos.res.CompraResponse;

import agricol.backend.entidades.ProductoinTransaccion;

@Mapper(componentModel = "Spring")
public interface ComprasMapper  {

    @Mapping(target = "producto.unid", source = "producto.unidComercializacion")
    CompraResponse toResponse(ProductoinTransaccion entity);

}
