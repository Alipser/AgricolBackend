package agricol.backend.utiles.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import agricol.backend.dtos.res.ProductoResponse;
import agricol.backend.dtos.res.ProductoResponseBasic;
import agricol.backend.entidades.Producto;


@Mapper(componentModel = "Spring")
public interface ProductoMapper {

    @Mapping(target = "unid", source = "unidComercializacion")
    ProductoResponse toResponse(Producto entity);

    @Mapping(source = "unidComercializacion", target = "unid") 
    ProductoResponseBasic productoToProductoResponseBasic(Producto producto);
    
    List<ProductoResponseBasic> productosToProductoResponseBasics(List<Producto> productos);
}
