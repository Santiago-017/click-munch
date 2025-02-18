"use client";
import { useState } from "react";
import { FaBars, FaSearch, FaTimes } from "react-icons/fa";

export default function Navbar() {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="flex h-screen">
      {/* Sidebar */}
      <div className={`bg-gray-800 text-white w-64 p-4 fixed h-full transition-transform ${isOpen ? "translate-x-0" : "-translate-x-64"} sm:translate-x-0`}>
        <button className="text-xl mb-4" onClick={() => setIsOpen(false)}>
          <FaTimes />
        </button>
        <ul className="mt-6 space-y-2">
          <li className="p-2 hover:bg-gray-700 cursor-pointer rounded">Inicio</li>
          <li className="p-2 hover:bg-gray-700 cursor-pointer rounded">Perfil</li>
          <li className="p-2 hover:bg-gray-700 cursor-pointer rounded">Configuración</li>
        </ul>
      </div>

      {/* Contenedor Principal */}
      <div className="flex-1 sm:ml-64">
        {/* Navbar */}
        <div className="bg-white shadow-md p-4 flex items-center justify-between">
          <button className="text-2xl sm:hidden" onClick={() => setIsOpen(true)}>
            <FaBars />
          </button>
          <div className="relative w-full max-w-md">
            <input
              type="text"
              placeholder="Buscar..."
              className="w-full p-2 pl-10 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <FaSearch className="absolute left-3 top-3 text-gray-500" />
          </div>
        </div>

        {/* Contenido Principal */}
        <div className="p-6">
          <h1 className="text-2xl font-bold">Contenido principal</h1>
          <p className="text-gray-600 mt-2">Aquí va el contenido de la página.</p>
        </div>
      </div>
    </div>
  );
}
