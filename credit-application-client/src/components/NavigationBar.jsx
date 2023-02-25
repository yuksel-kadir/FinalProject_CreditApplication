import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import React, { useState } from "react";
import logo from "../icons/bank_logo.svg";
import { Link } from "react-router-dom";
export default function NavigationBar() {
  const [isHomeActive, setHomeActive] = useState(true);
  const [isUpdateActive, setUpdateActive] = useState(false);
  const [isCreditCheckActive, setCreditCheckActive] = useState(false);

  const activateHome = () => {
    setHomeActive(true);
    setUpdateActive(false);
    setCreditCheckActive(false);
  };

  const activateCreditCheck = () => {
    setHomeActive(false);
    setUpdateActive(false);
    setCreditCheckActive(true);
  };

  const activateUpdate = () => {
    setHomeActive(false);
    setUpdateActive(true);
    setCreditCheckActive(false);
  };
  return (
    <Navbar bg="light" variant="light" className="navigationbar" fixed="top">
      <Container>
        <Navbar.Brand to="/creditApplication" as={Link} onClick={activateHome}>
          <img
            src={logo}
            width="50"
            height="50"
            className="d-inline-block align-top"
            alt="Bank Logo"
          />
        </Navbar.Brand>
        <Nav className="me-auto">
          <Nav.Link
            as={Link}
            to="/creditApplication"
            active={isHomeActive}
            onClick={activateHome}
          >
            Credit Application
          </Nav.Link>
          <Nav.Link
            as={Link}
            to="/updateInformations"
            active={isUpdateActive}
            onClick={activateUpdate}
          >
            Update Informations
          </Nav.Link>
          <Nav.Link
            as={Link}
            to="/checkCreditApplication"
            active={isCreditCheckActive}
            onClick={activateCreditCheck}
          >
            Check Credit Application
          </Nav.Link>
        </Nav>
      </Container>
    </Navbar>
  );
}
