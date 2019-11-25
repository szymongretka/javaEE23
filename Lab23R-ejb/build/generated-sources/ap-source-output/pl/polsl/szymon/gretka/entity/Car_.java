package pl.polsl.szymon.gretka.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.polsl.szymon.gretka.entity.CarShowroom;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-25T21:00:16")
@StaticMetamodel(Car.class)
public class Car_ { 

    public static volatile SingularAttribute<Car, String> colour;
    public static volatile SingularAttribute<Car, Integer> year;
    public static volatile SingularAttribute<Car, CarShowroom> carShowroom;
    public static volatile SingularAttribute<Car, String> model;
    public static volatile SingularAttribute<Car, Long> id;
    public static volatile SingularAttribute<Car, String> brand;

}