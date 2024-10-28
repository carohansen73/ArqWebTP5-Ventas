package com.example.demo.utils;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.example.demo.model.Venta;

@Component
public class PatcherVenta {

	public static Venta patchVenta(Map<String,Object> campos,Venta venta) {

		campos.forEach((k, v) -> {
			// use reflection to get field k on manager and set it to value v
			Field field = ReflectionUtils.findField(Venta.class, k);
			field.setAccessible(true);
			ReflectionUtils.setField(field, venta, v);
			field.setAccessible(false);
		});
		return venta;
	}

}

