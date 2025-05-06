INSERT INTO producto (
    nombre, codigo, descripcion, precio_unitario, laboratorio,
    principio_activo, presentacion, unidad_medida, stock_minimo,
    estado, controlado_por_lote
) VALUES
('Paracetamol 500mg', 'PROD001', 'Analgésico y antipirético usado para el dolor leve a moderado.', 1200.00, 'Genfar', 'Paracetamol', 'Tableta', 'mg', 50, 'ACTIVO', true),
('Ibuprofeno 400mg', 'PROD002', 'Antiinflamatorio no esteroideo (AINE).', 1500.00, 'MK', 'Ibuprofeno', 'Cápsula', 'mg', 30, 'ACTIVO', true),
('Jarabe para la tos', 'PROD003', 'Alivia la tos seca e irritativa.', 5600.00, 'La Santé', 'Dextrometorfano', 'Jarabe', 'ml', 20, 'ACTIVO', true),
('Alcohol Antiséptico 70%', 'PROD004', 'Solución antiséptica para desinfección.', 2500.00, 'Tecnoquímicas', 'Alcohol etílico', 'Líquido', 'ml', 10, 'ACTIVO', false),
('Algodón Hidrófilo 50g', 'PROD005', 'Algodón absorbente para uso médico.', 1800.00, 'Colmedica', NULL, 'Bolsa', 'g', 5, 'ACTIVO', false),
('Vitamina C 1000mg', 'PROD006', 'Suplemento alimenticio.', 3200.00, 'Nature’s Garden', 'Ácido ascórbico', 'Tableta efervescente', 'mg', 40, 'ACTIVO', true),
('Omeprazol 20mg', 'PROD007', 'Inhibidor de la bomba de protones para tratamiento de gastritis.', 1400.00, 'Siegfried Rhein', 'Omeprazol', 'Cápsula', 'mg', 25, 'ACTIVO', true),
('Solución Salina 0.9%', 'PROD008', 'Solución estéril para uso intravenoso.', 4200.00, 'Baxter', 'Cloruro de sodio', 'Bolsa', 'ml', 15, 'ACTIVO', false),
('Gasa Estéril 10x10cm', 'PROD009', 'Gasa estéril para cubrir heridas.', 800.00, 'SurgicalMed', NULL, 'Paquete', 'cm', 10, 'ACTIVO', false),
('Amoxicilina 500mg', 'PROD010', 'Antibiótico de amplio espectro.', 1700.00, 'Pfizer', 'Amoxicilina', 'Cápsula', 'mg', 30, 'ACTIVO', true);

INSERT INTO proveedor (
    nombre, nit, direccion, telefono, email, estado
) VALUES
('Distribuidora Salud Total S.A.', '900123456-1', 'Calle 45 #10-20, Bogotá', '3114567890', 'contacto@saludtotal.com', 'ACTIVO'),
('Farmacéutica Andina Ltda.', '800987654-2', 'Carrera 15 #65-32, Medellín', '3102345678', 'ventas@andinafarma.com', 'ACTIVO'),
('Drogas y Suministros S.A.S.', '901234321-3', 'Avenida 1 #23-45, Cali', '3023456789', 'info@drogasysuministros.com', 'ACTIVO'),
('Químicos del Norte S.A.', '890765432-4', 'Calle 100 #7-30, Barranquilla', '3001234567', 'atencion@quimicosnorte.com', 'ACTIVO'),
('BioSalud Ltda.', '812345678-5', 'Calle 12 #8-80, Bucaramanga', '3015678901', 'biosalud@biosalud.com', 'ACTIVO'),
('MediProveedores S.A.S.', '911223344-6', 'Carrera 50 #20-10, Cartagena', '3120001112', 'proveedores@medipro.com', 'ACTIVO'),
('TecnoFarma Colombia', '900444555-7', 'Diagonal 45 #20-33, Bogotá', '3133456677', 'ventas@tecnofarma.co', 'ACTIVO'),
('Suministros Hospitalarios Ltda.', '800666777-8', 'Cra 72 #45-60, Medellín', '3147788990', 'hospitalarios@suministros.com', 'ACTIVO'),
('SaludVital S.A.', '901122334-9', 'Av. Las Américas #100-01, Cali', '3201230004', 'servicio@saludvital.com', 'ACTIVO'),
('Distribuciones FarmaExpress', '900999888-0', 'Calle 60 #9-15, Pereira', '3162223334', 'farmexpress@distribuciones.com', 'ACTIVO');

INSERT INTO lote (
    producto_id, proveedor_id, numero_lote, fecha_ingreso, fecha_vencimiento, estado
) VALUES
(1, 1, 'LOT-202401-A', '2024-01-10', '2025-01-10', 'ACTIVO'),
(2, 2, 'LOT-202402-B', '2024-02-15', '2025-02-15', 'ACTIVO'),
(3, 3, 'LOT-202403-C', '2024-03-20', '2025-03-20', 'ACTIVO'),
(4, 4, 'LOT-202404-D', '2024-04-05', '2025-04-05', 'ACTIVO'),
(5, 5, 'LOT-202405-E', '2024-05-01', '2025-05-01', 'ACTIVO'),
(6, 6, 'LOT-202406-F', '2024-06-10', '2025-06-10', 'ACTIVO'),
(7, 7, 'LOT-202407-G', '2024-07-15', '2025-07-15', 'ACTIVO'),
(8, 8, 'LOT-202408-H', '2024-08-20', '2025-08-20', 'ACTIVO'),
(9, 9, 'LOT-202409-I', '2024-09-25', '2025-09-25', 'ACTIVO'),
(10, 10, 'LOT-202410-J', '2024-10-30', '2025-10-30', 'ACTIVO');

INSERT INTO movimiento_inventario (
    producto_id, lote_id, cantidad, precio_compra_venta, fecha, motivo, observaciones, tipo
) VALUES
(1, 1, 100, 1200.00, '2024-01-12', 'Compra inicial', 'Ingreso inicial de producto 1', 'ENTRADA'),
(1, 1, 20, 1200.00, '2024-01-20', 'Venta', 'Salida por venta a cliente', 'SALIDA'),
(2, 2, 150, 890.50, '2024-02-16', 'Reposición', 'Ingreso por reposición', 'ENTRADA'),
(3, NULL, 80, 300.00, '2024-03-22', 'Ajuste', 'Producto no controlado por lote', 'ENTRADA'),
(3, NULL, 30, 300.00, '2024-04-01', 'Venta', 'Salida parcial del producto 3', 'SALIDA'),
(4, 4, 200, 1350.75, '2024-04-07', 'Compra mayorista', 'Compra a proveedor externo', 'ENTRADA'),
(4, 4, 50, 1350.75, '2024-04-15', 'Devolución', 'Devolución de cliente', 'SALIDA'),
(5, 5, 90, 450.00, '2024-05-02', 'Compra regular', 'Ingreso de lote nuevo', 'ENTRADA'),
(6, NULL, 60, 500.00, '2024-06-12', 'Reposición interna', 'Producto no requiere lote', 'ENTRADA'),
(6, NULL, 10, 500.00, '2024-06-15', 'Salida por error', 'Ajuste de inventario', 'SALIDA');
