package agricol.backend.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agricol.backend.dtos.req.CompraRequest;
import agricol.backend.dtos.res.CompraResponse;
import agricol.backend.dtos.res.ProductoInTransaccionResponse;
import agricol.backend.dtos.res.TransaccionResponse;
import agricol.backend.entidades.Producto;
import agricol.backend.entidades.ProductoinTransaccion;
import agricol.backend.entidades.Transaccion;
import agricol.backend.entidades.Usuario;
import agricol.backend.exepciones.IdNotFoundException;
import agricol.backend.repositorios.ProductoRepositorio;
import agricol.backend.repositorios.ProductoTransaccionRepositorio;
import agricol.backend.repositorios.TransaccionRepositorio;
import agricol.backend.repositorios.UsuarioRepositorio;
import agricol.backend.servicios.Inetrfaz.ICompraServicio;
import agricol.backend.utiles.mappers.ComprasMapper;
import agricol.backend.utiles.mappers.TransaccionesMapper;
import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class CompraServicio implements ICompraServicio{

    @Autowired
    private final ComprasMapper comprasMapper;

    @Autowired
    private final TransaccionesMapper transaccionesMapper;

    @Autowired
    private final TransaccionRepositorio transaccionRepositorio;

    @Autowired
    private final ProductoTransaccionRepositorio compraRepositorio;

    @Autowired
    private final ProductoRepositorio productoRepositorio;
    
    @Autowired
    private final UsuarioRepositorio usuarioRepositorio;
    @Override
    @Transactional
    public CompraResponse abrirTransaccion(String idComprador, CompraRequest request) {
        
        Usuario usuarioComprador = usuarioRepositorio.findById(idComprador).orElseThrow(()-> new IdNotFoundException("Comprador"));
        Producto producto = productoRepositorio.findById(request.getProductoId()).orElseThrow(() -> new IdNotFoundException("Producto"));
        Usuario usuarioVendedor = usuarioRepositorio.findById(producto.getVendedor().getIdusuario()).orElseThrow(()-> new IdNotFoundException("Vendedor"));
        Transaccion transaccion = new Transaccion();
        

        transaccion.setUsuarioComprador(usuarioComprador);
        transaccion.setUsuarioVendedor(usuarioVendedor);
        transaccion.setMedioDePago(request.getTipoPago().name());
        transaccion.setFecha(LocalDate.now());

        Transaccion tCreada = transaccionRepositorio.save(transaccion);
        ProductoinTransaccion compra = new ProductoinTransaccion();
        compra.setCantidadComprada(request.getCantidad());
        compra.setComision(producto.getPrecio()* 0.019* request.getCantidad()); 
        compra.setProducto(producto);
        compra.setTransaccion(tCreada);
        
        compra.setTotal(producto.getPrecio()*request.getCantidad());
      
        CompraResponse compraresult = comprasMapper.toResponse(compraRepositorio.save(compra)); 
        producto.setCantidaDisponible(producto.getCantidaDisponible()-request.getCantidad());
        productoRepositorio.save(producto);
        tCreada.setTotal(producto.getPrecio()*request.getCantidad());
        transaccionRepositorio.save(tCreada);

        return compraresult;
    }
    @Override
    public List<TransaccionResponse> misCompras(String idComprador) {
        Usuario usuarioComprador = usuarioRepositorio.findById(idComprador).orElseThrow(()-> new IdNotFoundException("Comprador"));
        List<Transaccion> misCompras = transaccionRepositorio.findByUsuarioComprador(usuarioComprador);
        

        return transaccionesMapper.lsitaToListaResponse(misCompras);
    }

   
   

    
    
    
}
