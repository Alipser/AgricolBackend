package agricol.backend.dtos.res;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraResponse {
    private int id;
    private double cantidadComprada;
    private double comision;
    private ProductoResponse producto;

}