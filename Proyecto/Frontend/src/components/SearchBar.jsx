import React from "react";
import { Form, InputGroup } from "react-bootstrap";

function SearchBar({ searchTerm, setSearchTerm }) {
  return (
    <InputGroup className="mb-3">
      <Form.Control
        type="text"
        placeholder="Buscar por nombre o dirección..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
    </InputGroup>
  );
}

export default SearchBar;
