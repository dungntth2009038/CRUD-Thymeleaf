package comt2009m1.demo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "admins")
public class AdminStudent {
    @Id
    private int id;
    private String buy_name;
    private int phone_Number;
    private String emai;
    private String product_Name;
    private LocalDateTime order_Time;
    private int status;
}
