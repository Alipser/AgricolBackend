package agricol.backend.entidades;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity (name ="productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idproducto;

    private String nombreProducto;
    private String clasificacion;
    private double cantidaDisponible;
    private String localizacion;
    private double precio;
    private String imagenUrl;
    
    @ManyToOne
    @JoinColumn(name = "vendedor_id", referencedColumnName = "idusuario")
    private Usuario vendedor;
    //USUARIO QUE CREA EL PRODUCTO

    @ManyToOne
    @JoinColumn(name = "unidadcomercializacion_id", referencedColumnName = "id")
    private UnidComercializacion unidComercializacion;

    @OneToMany(mappedBy = "producto")
    private List<ProductoinTransaccion> productoInTransaccion;

}