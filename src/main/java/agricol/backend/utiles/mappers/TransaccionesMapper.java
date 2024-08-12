package agricol.backend.utiles.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import agricol.backend.dtos.res.TransaccionResponse;

import agricol.backend.entidades.Transaccion;

@Mapper(componentModel = "Spring")
public interface TransaccionesMapper extends ProductoMapper{

    @Mapping(target = "listaProductosTransacciones", source = "listaTransacciones")
    @Mapping(target = "usuarioCompradorId", source = "usuarioComprador.nombre")
    @Mapping(target = "usuarioVendedorId", source = "usuarioComprador.nombre")
    TransaccionResponse toResponse(Transaccion transaccion);

    List<TransaccionResponse> lsitaToListaResponse(List<Transaccion> listaTransacciones);

}
