package agricol.backend.servicios;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agricol.backend.dtos.req.ProductoInTransaccionRequest;
import agricol.backend.dtos.req.TransaccionRequest;
import agricol.backend.dtos.res.ProductoInTransaccionResponse;
import agricol.backend.dtos.res.ProductoResponseBasic;
import agricol.backend.dtos.res.TransaccionResponse;
import agricol.backend.dtos.res.UsuarioResponse;
import agricol.backend.entidades.Producto;
import agricol.backend.entidades.ProductoinTransaccion;
import agricol.backend.entidades.Transaccion;
import agricol.backend.entidades.Usuario;
import agricol.backend.exepciones.IdNotFoundException;
import agricol.backend.repositorios.ProductoRepositorio;
import agricol.backend.repositorios.ProductoTransaccionRepositorio;
import agricol.backend.repositorios.TransaccionRepositorio;
import agricol.backend.repositorios.UsuarioRepositorio;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class TransaccionServicio implements ServicioAbstracto<TransaccionRequest, TransaccionResponse, Integer>{

    @Autowired
    private final TransaccionRepositorio transaccionRepositorio;

    @Autowired
    private final UsuarioRepositorio usuariorepositorio;

    @Autowired
    private final ProductoTransaccionRepositorio productoTransaccionRepositorio;

    @Autowired
    private final ProductoRepositorio productoRepositorio;

    @Override
    public Page<TransaccionResponse> getAll(int page, int size) {
        PageRequest pagination = PageRequest.of(page, size);
        Page<Transaccion> transacciones = transaccionRepositorio.findAll(pagination);
        return transacciones.map(this::convertTransaccionToResponse);
    }



    @Override
    public TransaccionResponse create(TransaccionRequest request) {
        Usuario usuarioComprador = usuariorepositorio.findById(request.getUsuarioCompradorId()).orElseThrow(()-> new IdNotFoundException("usuario"));

        Usuario usuarioVendedor = usuariorepositorio.findById(request.getUsuarioVendedorId()).orElseThrow(()-> new IdNotFoundException("usuario"));

        Transaccion transaccion = convertTransaccionRequestToEntity(request, usuarioComprador, usuarioVendedor);
        Transaccion transaccionCreada =  transaccionRepositorio.save(transaccion);

        request.getListaTransacciones();

        List<ProductoinTransaccion> productsInTransaccion = request.getListaTransacciones().stream().map(e ->{
            Producto guarda = productoRepositorio.findById(e.getProductoId()).orElseThrow(()-> new IdNotFoundException("Producto ")) ;
            ProductoinTransaccion response = convertProductoInTransaccionRequestToEntity(e, transaccionCreada, guarda);
            return response;
        }).toList();

        productoTransaccionRepositorio.saveAll(productsInTransaccion);



        return convertTransaccionToResponse(transaccionCreada);
        
    }

    @Override
    public TransaccionResponse update(TransaccionRequest request, Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public TransaccionResponse getById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    public Transaccion convertTransaccionRequestToEntity(TransaccionRequest request, Usuario usuarioComprador, Usuario usuarioVendedor){
        Transaccion transaccion = new Transaccion();
        BeanUtils.copyProperties(request, transaccion);
        transaccion.setUsuarioComprador(usuarioComprador);
        transaccion.setUsuarioVendedor(usuarioVendedor);
        return transaccion;
    }
    public TransaccionResponse convertTransaccionToResponse(Transaccion transaccion) {
        TransaccionResponse response = new TransaccionResponse();
        BeanUtils.copyProperties(transaccion, response);
        response.setUsuarioCompradorId(convertUsuarioToResponse(transaccion.getUsuarioComprador()).getNombre());
        response.setUsuarioVendedorId(convertUsuarioToResponse(transaccion.getUsuarioVendedor()).getNombre());
        response.setListaProductosTransacciones(transaccion.getListaTransacciones().stream().map(e -> {
            ProductoInTransaccionResponse newResponse = convertProductoInTransaccionToResponse(e);
            newResponse.setProducto(convertProductoToResponseBasic(e.getProducto()));
            return newResponse;
        } ).toList());
        return response;
    }

    private UsuarioResponse convertUsuarioToResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        BeanUtils.copyProperties(usuario, response);
        response.setRol(usuario.getRol());
        return response;
    }

    private ProductoInTransaccionResponse convertProductoInTransaccionToResponse(ProductoinTransaccion producto){
        ProductoInTransaccionResponse response = new ProductoInTransaccionResponse();
        ProductoResponseBasic basicResponse = new ProductoResponseBasic();
        BeanUtils.copyProperties(producto, response);
        BeanUtils.copyProperties(producto.getProducto(), basicResponse);
        response.setProducto(basicResponse);
        return response;
    }

    private ProductoResponseBasic convertProductoToResponseBasic(Producto producto){
        ProductoResponseBasic response = new ProductoResponseBasic();
        BeanUtils.copyProperties(producto, response);
        return response;
    }

    private ProductoinTransaccion convertProductoInTransaccionRequestToEntity(ProductoInTransaccionRequest request, Transaccion transaccion, Producto productForSaving){
        ProductoinTransaccion producto = new ProductoinTransaccion();
        BeanUtils.copyProperties(request, producto);
        producto.setTransaccion(transaccion);
        producto.setProducto(productForSaving);
        return producto;
    }
}




