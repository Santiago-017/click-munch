CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(50) CHECK (tipo_usuario IN ('cliente', 'cocina', 'admin')) NOT NULL
);

CREATE TABLE restaurantes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion TEXT NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    administrador_id INT REFERENCES usuarios(id) ON DELETE SET NULL
);

CREATE TABLE menus (
    id SERIAL PRIMARY KEY,
    restaurante_id INT REFERENCES restaurantes(id) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL
);

CREATE TABLE platillos (
    id SERIAL PRIMARY KEY,
    menu_id INT REFERENCES menus(id) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    imagen_url TEXT
);

CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    restaurante_id INT REFERENCES restaurantes(id) ON DELETE CASCADE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    estado VARCHAR(50) CHECK (estado IN ('pendiente', 'preparando', 'entregado')) NOT NULL
);

CREATE TABLE detalles_pedido (
    id SERIAL PRIMARY KEY,
    pedido_id INT REFERENCES pedidos(id) ON DELETE CASCADE,
    platillo_id INT REFERENCES platillos(id) ON DELETE CASCADE,
    cantidad INT NOT NULL CHECK (cantidad > 0)
);

CREATE TABLE reservas (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    restaurante_id INT REFERENCES restaurantes(id) ON DELETE CASCADE,
    fecha_hora TIMESTAMP NOT NULL,
    numero_personas INT NOT NULL CHECK (numero_personas > 0)
);

CREATE TABLE valoraciones (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    restaurante_id INT REFERENCES restaurantes(id) ON DELETE CASCADE,
    puntuacion INT CHECK (puntuacion BETWEEN 1 AND 5) NOT NULL,
    comentario TEXT
);