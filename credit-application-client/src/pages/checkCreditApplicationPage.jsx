import { React, useState } from "react";
import { Button, Modal, Form, Row, Col, Alert } from "react-bootstrap";
import DatePicker from "react-datepicker";
import { getCreditApplication } from "../api/creditApplicationApi";
import moment from "moment";

export default function CheckCreditApplicationPage() {
  const [identity, setIdentity] = useState("43945465268");
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [dob, setDob] = useState(moment(selectedDate).format("YYYY-MM-DD"));
  //Found credit application infos
  const [clientName, setClientName] = useState();
  const [clientLastName, setClientLastName] = useState();
  const [creditScore, setcreditScore] = useState();
  const [creditLimit, setCreditLimit] = useState(0);
  const [creditStatus, setCreditStatus] = useState("");
  //Result modal variables and function
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  //Error alert variables and function
  const [showAlert, setShowAlert] = useState(false);
  const [errorResponse, setErrorResponse] = useState();

  const findCreditApplication = async (event) => {
    event.preventDefault();
    console.log("identity: " + identity);
    const requestBody = {
      identityNumber: identity,
      dateOfBirth: dob,
    };

    try {
      const response = await getCreditApplication(requestBody);
      console.log("found credit application: ");
      console.log(response.data);
      setClientName(response.data.resultData.firstName);
      setClientLastName(response.data.resultData.lastName);
      setcreditScore(response.data.resultData.creditScore);
      setCreditLimit(response.data.resultData.creditLimit);
      setCreditStatus(response.data.resultData.creditStatus);
      handleShow();
    } catch (error) {
      console.log("error");
      console.log(error);
      setShowAlert(true);
      setErrorResponse(error.response.data.message);
    }
  };

  const setBirthDate = (date) => {
    setDob(moment(date).format("YYYY-MM-DD"));
    setSelectedDate(date);
  };
  return (
    <div className="checkCreditApplicationPage">
      <Form className="creditapplicationform" onSubmit={findCreditApplication}>
        <Row>
          <Col>
            <Form.Group className="mb-3" controlId="formIdentity">
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
          <Col>
            <Form.Group className="mb-3" controlId="formBirthDate">
              <Form.Label>Date of Birth (Year/Month/Day)</Form.Label>
              <DatePicker
                selected={selectedDate}
                dateFormat="yyyy/MM/dd"
                onChange={(date) => setBirthDate(date)}
              />
            </Form.Group>
          </Col>
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
          <h4>
            Client Full Name : {clientName} {clientLastName}
          </h4>
          <h4>Credit Score : {creditScore}</h4>
          <h4>Credit Application Status : {creditStatus}</h4>
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
