import { Button, Modal, Form, Row, Col, Alert } from "react-bootstrap";
import { React, useState } from "react";
import {
  updateFirstName,
  updateLastName,
  updatePhoneNumber,
  updateCreditApplication
} from "../api/creditApplicationApi";

export default function UpdateInformationPage() {
  const [identity_1, setIdentity_1] = useState();
  const [identity_2, setIdentity_2] = useState();
  const [identity_3, setIdentity_3] = useState();
  const [identity_4, setIdentity_4] = useState();

  const [show_1, setShow_1] = useState(false);
  const handleClose_1 = () => setShow_1(false);
  const handleShow_1 = () => setShow_1(true);

  const [show_2, setShow_2] = useState(false);
  const handleClose_2 = () => setShow_2(false);
  const handleShow_2 = () => setShow_2(true);

  const [show_3, setShow_3] = useState(false);
  const handleClose_3 = () => setShow_3(false);
  const handleShow_3 = () => setShow_3(true);

  const [show_4, setShow_4] = useState(false);
  const handleClose_4 = () => setShow_4(false);
  const handleShow_4 = () => setShow_4(true);

  //Handle alert status
  const [showAlert_1, setShowAlert_1] = useState(false);
  const [showAlert_2, setShowAlert_2] = useState(false);
  const [showAlert_3, setShowAlert_3] = useState(false);
  const [showAlert_4, setShowAlert_4] = useState(false);

  const [firstName, setFirstName] = useState();
  const [responseFirstName, setResponseFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [responseLastName, setResponseLastName] = useState();
  const [phoneNumber, setPhoneNumber] = useState();
  const [responsePhoneNumber, setResponsePhoneNumber] = useState();
  const [monthlyIncome, setMonthlyIncome] = useState();
  const [collateral, setCollateral] = useState();

  //Credit application result variables
  const [creditLimit, setCreditLimit] = useState(0);
  const [creditStatus, setCreditStatus] = useState("");

  const [errorResponse_1, setErrorResponse_1] = useState();
  const [errorResponse_2, setErrorResponse_2] = useState();
  const [errorResponse_3, setErrorResponse_3] = useState();
  const [errorResponse_4, setErrorResponse_4] = useState();

  const updateClientFirstName = async (event) => {
    event.preventDefault();
    setShowAlert_1(false);
    const updateParams = {
      identityNumber: identity_1,
      input: firstName,
    };
    try {
      const response = await updateFirstName(updateParams);
      console.log("update client first name response");
      console.log(response);
      setResponseFirstName(response.data.resultData);
      setShowAlert_1(false);
      handleShow_1();
    } catch (error) {
      console.log("update client error");
      console.log(error);
      setErrorResponse_1(error.response.data.message);
      setShowAlert_1(true);
    }
  };

  const updateClientLastName = async (event) => {
    event.preventDefault();
    setShowAlert_2(false);
    const updateParams = {
      identityNumber: identity_2,
      input: lastName,
    };
    try {
      const response = await updateLastName(updateParams);
      console.log("update client last name response");
      console.log(response);
      setResponseLastName(response.data.resultData);
      setShowAlert_2(false);
      handleShow_2();
    } catch (error) {
      console.log("update last name error");
      console.log(error);
      console.log(error.response.data.message);
      setErrorResponse_2(error.response.data.message);
      setShowAlert_2(true);
    }
  };

  const updateClientPhoneNumber = async (event) => {
    event.preventDefault();
    setShowAlert_3(false);
    const updateParams = {
      identityNumber: identity_3,
      phoneNumber: phoneNumber,
    };
    try {
      const response = await updatePhoneNumber(updateParams);
      console.log("update client phone number response");
      console.log(response);
      setResponsePhoneNumber(response.data.resultData);
      setShowAlert_3(false);
      handleShow_3();
    } catch (error) {
      console.log("update phone number error");
      console.log(error);
      console.log(error.response.data.message);
      setErrorResponse_3(error.response.data.message);
      setShowAlert_3(true);
    }
  };

  const updateClientCreditApplication = async (event) => {
    event.preventDefault();
    setShowAlert_4(false);
    const updateParams = {
      identityNumber: identity_4,
      monthlyIncome: monthlyIncome,
      collateral: collateral
    };
    try {
        const response = await updateCreditApplication(updateParams);
        console.log("update client credit application response");
        console.log(response);
        //setResponsePhoneNumber(response.data.resultData);
        setCreditStatus(response.data.resultData.creditStatus);
        setCreditLimit(response.data.resultData.creditLimit);
        setShowAlert_4(false);
        handleShow_4();
      } catch (error) {
        console.log("update credit application error");
        console.log(error);
        console.log(error.response.data.message);
        setErrorResponse_4(error.response.data.message);
        setShowAlert_4(true);
      }
  }
  return (
    <div className="updateInformationPage">
      <Row>
        <Col>
          <Form
            className="changeFirstNameForm"
            onSubmit={updateClientFirstName}
          >
            <Form.Group className="mb-3" controlId="formIdentity">
              <Form.Label>Identity Number</Form.Label>
              <Form.Control
                onChange={(event) => setIdentity_1(event.target.value)}
                required
                type="text"
                maxLength="11"
                placeholder="Your identity number"
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formIdentity">
              <Form.Label>New First Name</Form.Label>
              <Form.Control
                onChange={(event) => setFirstName(event.target.value)}
                required
                type="text"
                placeholder="Enter a name"
              />
            </Form.Group>
            <Button variant="primary" type="submit">
              Change First Name
            </Button>
            <Alert
              className="mt-3"
              show={showAlert_1}
              variant="warning"
              onClose={() => setShowAlert_1(false)}
              dismissible
            >
              Error : {errorResponse_1}
            </Alert>
          </Form>
          <Modal show={show_1} onHide={handleClose_1}>
            <Modal.Header closeButton>
              <Modal.Title>Update Information</Modal.Title>
            </Modal.Header>
            <Modal.Body id="updateInformationModalId">
              <h4>Full name is updated to: {responseFirstName}</h4>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="primary" onClick={handleClose_1}>
                Close
              </Button>
            </Modal.Footer>
          </Modal>
        </Col>
        <Col>
          <Form className="changeLastNameForm" onSubmit={updateClientLastName}>
            <Form.Group className="mb-3" controlId="formIdentity">
              <Form.Label>Identity Number</Form.Label>
              <Form.Control
                onChange={(event) => setIdentity_2(event.target.value)}
                required
                type="text"
                maxLength="11"
                placeholder="Your identity number"
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formIdentity">
              <Form.Label>New Last Name</Form.Label>
              <Form.Control
                onChange={(event) => setLastName(event.target.value)}
                required
                type="text"
                placeholder="Enter a last name"
              />
            </Form.Group>
            <Button variant="primary" type="submit">
              Change Last Name
            </Button>
            <Alert
              className="mt-3"
              show={showAlert_2}
              variant="warning"
              onClose={() => setShowAlert_2(false)}
              dismissible
            >
              Error : {errorResponse_2}
            </Alert>
          </Form>
          <Modal show={show_2} onHide={handleClose_2}>
            <Modal.Header closeButton>
              <Modal.Title>Update Information</Modal.Title>
            </Modal.Header>
            <Modal.Body id="updateInformationModalId">
              <h4>Full name is updated to: {responseLastName}</h4>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="primary" onClick={handleClose_2}>
                Close
              </Button>
            </Modal.Footer>
          </Modal>
        </Col>
        <Col>
          <Form
            className="changePhoneNumberForm"
            onSubmit={updateClientPhoneNumber}
          >
            <Form.Group className="mb-3" controlId="formIdentity">
              <Form.Label>Identity Number</Form.Label>
              <Form.Control
                onChange={(event) => setIdentity_3(event.target.value)}
                required
                type="text"
                maxLength="11"
                placeholder="Your identity number"
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formIdentity">
              <Form.Label>New Phone Number</Form.Label>
              <Form.Control
                onChange={(event) => setPhoneNumber(event.target.value)}
                required
                type="text"
                maxLength="11"
                placeholder="Enter a phone number"
              />
            </Form.Group>
            <Button variant="primary" type="submit">
              Change Phone Number
            </Button>
            <Alert
              className="mt-3"
              show={showAlert_3}
              variant="warning"
              onClose={() => setShowAlert_3(false)}
              dismissible
            >
              Error : {errorResponse_3}
            </Alert>
          </Form>
          <Modal show={show_3} onHide={handleClose_3}>
            <Modal.Header closeButton>
              <Modal.Title>Update Information</Modal.Title>
            </Modal.Header>
            <Modal.Body id="updateInformationModalId">
              <h4>Phone number is updated to: {responsePhoneNumber}</h4>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="primary" onClick={handleClose_3}>
                Close
              </Button>
            </Modal.Footer>
          </Modal>
        </Col>
        <Col>
          <Form className="updateCreditApplication" onSubmit={updateClientCreditApplication}>
            <Form.Group className="mb-3" controlId="formIdentity">
              <Form.Label>Identity Number</Form.Label>
              <Form.Control
                onChange={(event) => setIdentity_4(event.target.value)}
                required
                type="text"
                maxLength="11"
                placeholder="Your identity number"
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formIdentity">
              <Form.Label>New Monthly Income</Form.Label>
              <Form.Control
                onChange={(event) => setMonthlyIncome(event.target.value)}
                required
                type="text"
                maxLength="11"
                placeholder="Enter income"
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formIdentity">
              <Form.Label>New Collateral</Form.Label>
              <Form.Control
                onChange={(event) => setCollateral(event.target.value)}
                required
                type="text"
                maxLength="11"
                placeholder="Enter collateral"
              />
            </Form.Group>
            <Button variant="primary" type="submit">
              Update Application
            </Button>
            <Alert
              className="mt-3"
              show={showAlert_4}
              variant="warning"
              onClose={() => setShowAlert_4(false)}
              dismissible
            >
              Error : {errorResponse_4}
            </Alert>
          </Form>
          <Modal show={show_4} onHide={handleClose_4}>
            <Modal.Header closeButton>
              <Modal.Title>Updated Credit Application Information</Modal.Title>
            </Modal.Header>
            <Modal.Body id="updateInformationModalId">
              <h4>Credit Application Status : {creditStatus}</h4>
              <h4>Credit Limit : {creditLimit}</h4>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="primary" onClick={handleClose_4}>
                Close
              </Button>
            </Modal.Footer>
          </Modal>
        </Col>
      </Row>
    </div>
  );
}
