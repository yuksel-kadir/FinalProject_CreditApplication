import { React, useState } from "react";
import { Button, Modal, Form, Row, Col, Alert } from "react-bootstrap";
import DatePicker from "react-datepicker";
import moment from "moment";
import { postCreditApplication } from "../api/creditApplicationApi";

export default function Homepage() {
  const [name, setName] = useState("Kadir");
  const [lastName, setLastName] = useState("Yüksel");
  const [identity, setIdentity] = useState("43945465268");
  const [monthlyIncome, setMonthlyIncome] = useState(0);
  const [collateral, setCollateral] = useState(0);
  const [phoneNumber, setPhoneNumber] = useState("05079788883");
  const [dob, setDob] = useState(new Date());
  const [dobRequest, setDobRequest] = useState(
    moment(dob).format("YYYY-MM-DD")
  );
  //Credit application result variables
  const [creditLimit, setCreditLimit] = useState(0);
  const [applicationStatus, setApplicationStatus] = useState("");
  const [errorResponse, setErrorResponse] = useState();
  //Handle modal appearance
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  //Handle alert status
  const [showAlert, setShowAlert] = useState(false);
  //Create credit application and send to the server.
  const createCreditApplication = async (event) => {
    event.preventDefault();
    setShowAlert(false);
    const creditApplication = {
      firstName: name,
      lastName: lastName,
      identityNumber: identity,
      phoneNumber: phoneNumber,
      dateOfBirth: dobRequest,
      monthlyIncome: monthlyIncome,
      collateral: collateral,
    };

    try {
      const response = await postCreditApplication(creditApplication);
      console.log(response.data);
      setCreditLimit(response.data.resultData.creditLimit);
      setApplicationStatus(response.data.resultData.creditStatus);
      handleShow();
    } catch (error) {
      console.log(error);
      setErrorResponse(error.response.data.message);
      setShowAlert(true);
    }
  };
  const setDate = (date) => {
    setDob(date);
    setDobRequest(moment(date).format("YYYY-MM-DD"))
  }
  return (
    <div className="creditApplicationPage">
      <Form
        className="creditapplicationform"
        onSubmit={createCreditApplication}
      >
        <Row>
          <Col>
            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>First Name</Form.Label>
              <Form.Control
                onChange={(event) => setName(event.target.value)}
                required
                type="text"
                placeholder="Enter your name"
                defaultValue="Kadir"
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>Last Name</Form.Label>
              <Form.Control
                onChange={(event) => setLastName(event.target.value)}
                required
                type="text"
                placeholder="Enter your last name"
                defaultValue="Yüksel"
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>Identity Number</Form.Label>
              <Form.Control
                onChange={(event) => setIdentity(event.target.value)}
                required
                type="text"
                maxLength="11"
                placeholder="Enter your identity number"
                defaultValue="43945465268"
              />
            </Form.Group>
          </Col>
        </Row>
        <Row>
          <Col>
            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>Phone Number</Form.Label>
              <Form.Control
                onChange={(event) => setPhoneNumber(event.target.value)}
                required
                type="text"
                maxLength="11"
                placeholder="Enter your phone number"
                defaultValue="05079788883"
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>Monthly Income</Form.Label>
              <Form.Control
                onChange={(event) => setMonthlyIncome(event.target.value)}
                required
                type="text"
                placeholder="Enter your monthly income"
                defaultValue="0"
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>Collateral</Form.Label>
              <Form.Control
                onChange={(event) => setCollateral(event.target.value)}
                required
                type="text"
                placeholder="Enter your collateral"
                defaultValue="0"
              />
            </Form.Group>
          </Col>
        </Row>
        <Row>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Date of Birth(Year/Month/Day)</Form.Label>
            <DatePicker
              selected={dob}
              dateFormat="yyyy/MM/dd"
              onChange={(date) => setDate(date)}
            />
          </Form.Group>
        </Row>
        <Button variant="primary" type="submit">
          Submit
        </Button>
        <Alert
          className="mt-3"
          show={showAlert}
          variant="warning"
          onClose={() => setShowAlert(false)}
          dismissible
        >
          Error : {errorResponse}
        </Alert>
      </Form>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Credit Application Result</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <h4>Credit Application Status : {applicationStatus}</h4>
          <h4>Credit Limit : {creditLimit}</h4>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}
