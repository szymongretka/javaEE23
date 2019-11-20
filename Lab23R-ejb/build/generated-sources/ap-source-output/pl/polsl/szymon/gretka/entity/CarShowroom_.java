package pl.polsl.szymon.gretka.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.polsl.szymon.gretka.entity.Car;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-20T22:44:08")
@StaticMetamodel(CarShowroom.class)
public class CarShowroom_ { 

    public static volatile ListAttribute<CarShowroom, Car> cars;
    public static volatile SingularAttribute<CarShowroom, String> city;
    public static volatile SingularAttribute<CarShowroom, String> street;
    public static volatile SingularAttribute<CarShowroom, String> name;
    public static volatile SingularAttribute<CarShowroom, Long> id;

}