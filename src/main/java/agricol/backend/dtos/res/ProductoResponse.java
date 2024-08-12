package agricol.backend.dtos.res;

import agricol.backend.entidades.UnidComercializacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {
    private int idproducto;
    private String nombreProducto;
    private String clasificacion;
    private double cantidaDisponible;
    private String localizacion;
    private double precio;
    private UnidComercializacion unid;
    private UsuarioResponse vendedor;
    private String imagenUrl;
}
