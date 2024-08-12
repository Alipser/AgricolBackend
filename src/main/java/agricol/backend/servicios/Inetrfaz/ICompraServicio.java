package agricol.backend.servicios.Inetrfaz;

import java.util.List;

import agricol.backend.dtos.req.CompraRequest;
import agricol.backend.dtos.res.CompraResponse;
import agricol.backend.dtos.res.TransaccionResponse;


public interface ICompraServicio  {

    CompraResponse abrirTransaccion( String idComprador ,CompraRequest request);

    List<TransaccionResponse> misCompras(String idComprador);
    
}
