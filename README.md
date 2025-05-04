## 📦 Entidades del Sistema
1. Producto

2. Lote

3. Proveedor

4. MovimientoInventario

## ✅ Requerimientos Funcionales
🔍 Listado General
Todas las entidades deben poder listarse con:

Paginación en grupos de 10 elementos.

Orden alfabético si se ordena por campos de tipo String.

## 🧾 Producto
Listar todos los productos.

Buscar producto:

* Por id.

Filtrar productos:

* Por nombre.

* Listar por código.

## 📦 Lote
Listar todos los lotes:

* En orden descendente por fecha de ingreso.

* En orden ascendente por fecha de vencimiento.

Buscar lote:

* Por id.

* Por numeroLote.

## 🧍 Proveedor
Listar proveedores por nombre.

Buscar proveedor:

* Por id.

* Por NIT.

Filtrar proveedor:

* Por nombre.

## 🔁 MovimientoInventario
Listar todos:

* En orden descendente por fecha.

Listar filtrando:

* Por tipo (orden por fecha descendente).

* Por id (orden por fecha descendente).

* Por lote (orden por fecha descendente).

* Por producto (orden por fecha descendente).

Buscar por id.