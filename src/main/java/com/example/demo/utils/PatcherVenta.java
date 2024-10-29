package com.example.demo.utils;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.example.demo.model.Venta;

@Component
public class PatcherVenta {

	public static Venta patchVenta(Venta ventaIncompleta,Venta venta) {

		//GET THE COMPILED VERSION OF THE CLASS
        Class<?> ventaClass= Venta.class;
        Field[] ventaFields=ventaClass.getDeclaredFields();
        System.out.println(ventaFields.length);
        for(Field field : ventaFields){
            System.out.println(field.getName());
            //CANT ACCESS IF THE FIELD IS PRIVATE
            field.setAccessible(true);

            //CHECK IF THE VALUE OF THE FIELD IS NOT NULL, IF NOT UPDATE EXISTING INTERN
            try {
            	Object value=field.get(ventaIncompleta);
            	if(value!=null){
            		field.set(venta,value);
            	}            	
            }catch(Exception e) {
            	
            }
            //MAKE THE FIELD PRIVATE AGAIN
            field.setAccessible(false);
        }
        return venta;

    }

}

