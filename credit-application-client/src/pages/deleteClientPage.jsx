import { React, useState } from "react";
import { Button, Modal, Form, Row, Col, Alert } from "react-bootstrap";
import { deleteClient } from "../api/creditApplicationApi";
export default function DeleteClientPage() {
  const [identity, setIdentity] = useState("43945465268");
  //Handle modal appearance
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  //Handle alert status
  const [showAlert, setShowAlert] = useState(false);
  const [errorResponse, setErrorResponse] = useState();
  const [responseMessage, setResponseMessage] = useState();

  const deleteClientFunc = async (event) => {
    event.preventDefault();
    setShowAlert(false);
    const requestBody = {
      identityNumber: identity,
    };
    try {
      const response = await deleteClient(requestBody);
      console.log("delete client response");
      console.log(response.data);
    } catch (error) {
        console.log("deleteClient error")
        console.log(error);
        console.log(error.response.data.message);
        setErrorResponse(error.response.data.message);
        setShowAlert(true);
    }
  };

  return (
    <div className="deleteClientPage">
      <Form className="deleteClientForm" onSubmit={deleteClientFunc}>
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
        </Row>
        <Button variant="primary" type="submit">
          Delete Client
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
            {responseMessage}
          </h4>
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
