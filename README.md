
---

# Sistema de Inventario "Tiendita MeliLu" 

Este proyecto es una aplicación de escritorio desarrollada en **JavaFX** para la gestión de inventarios. Permite administrar productos de forma local mediante un sistema CRUD (Altas, Bajas, Consultas y Cambios) con persistencia de datos en archivos planos, cumpliendo con los requerimientos de la materia integradora.

## Descripción del Sistema
La aplicación está diseñada para pequeñas tiendas que requieren un control de stock sin dependencia de internet ni bases de datos complejas. Utiliza una arquitectura de capas separando la interfaz (FXML/Controllers), la lógica de negocio (Services) y las reglas de validación (Models).

## Cómo funciona la aplicación
Una vez ejecutado el programa, el sistema carga automáticamente los datos existentes desde el archivo CSV. El flujo de trabajo es el siguiente:

1.  **Carga Inicial:** Al abrir la aplicación, se leen los registros del archivo y se muestran en la tabla principal.
2.  **Agregar Producto:** * Se presiona el botón **Agregar**.
    * Se abre un nuevo formulario donde se deben llenar todos los datos del producto (ID, Nombre, Stock, Precio y Categoría).
    * Al finalizar, se da clic en el botón **Agregar** dentro del formulario para guardar el registro.
3.  **Eliminar Producto:** * Se presiona el botón **Eliminar**.
    * En el nuevo formulario, se debe ingresar el **ID**, el **Nombre** o ambos campos para identificar el producto.
    * Se presiona el botón **Eliminar** para confirmar la baja.
4.  **Actualizar Producto:** * Se debe **seleccionar una fila** directamente en la tabla principal.
    * Al dar clic en el botón **Actualizar**, se abre un formulario con los campos automáticamente llenos con la información del producto seleccionado.
    * Se realizan los cambios necesarios y se presiona el botón **Actualizar** para guardar los cambios en tiempo real.
5.  **Búsqueda y Filtrado:** * En la parte superior de la tabla se encuentran los campos de **ID** y **Nombre**.
    * Se puede buscar por uno solo o por ambos. El filtro es flexible: si ingresas "1", se mostrarán los productos con ID 1, 11, 21, etc.
    * Si se llenan ambos campos, el filtro será específico para ese producto.
    * Para limpiar la búsqueda y "regresar" a la lista completa, se deben dejar los campos vacíos y presionar el botón **Buscar**.

##  Archivo de Persistencia
El sistema utiliza un archivo de texto estructurado para garantizar que la información no se pierda.

* **Ubicación:** `/data/productos.csv` (en la raíz del proyecto).
* **Formato:** CSV (Valores separados por comas).
* **Estructura:** `id,nombre,stock,precio,categoria`
* **Sincronización:** Cualquier cambio realizado se escribe inmediatamente en el archivo y se refresca en la tabla principal.

## ️ Validaciones Implementadas
El sistema cuenta con un modelo de validación que verifica:
* Campos obligatorios (no vacíos).
* ID alfanumérico y único (no repetido en altas).
* Nombre con mínimo 3 caracteres y sin símbolos especiales (`@!$#`).
* Stock y Precio numéricos y positivos.

---
**Desarrollado por:** 
* Melisa Casandra Sánchez Vázquez
* Lucero Bahena Santana

**Grupo:** 2°C – DSM

---