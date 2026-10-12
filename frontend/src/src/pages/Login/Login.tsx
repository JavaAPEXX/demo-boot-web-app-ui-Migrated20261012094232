import React, { useState } from 'react';
import { Button, Form, FormGroup, Label, Input, Alert } from 'react-bootstrap';
import { useHistory } from 'react-router-dom';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');
  const [showError, setShowError] = useState(false);
  const history = useHistory();

  const handleSubmit = (e) => {
    e.preventDefault();
    // Simulate login logic
    const user = { username, password };
    // Replace with actual backend call
    fetch('/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          history.push('/welcome');
        } else {
          setError(data.error);
          setShowError(true);
        }
      })
      .catch((err) => {
        console.error('Error:', err);
        setError('An error occurred during login.');
        setShowError(true);
      });
  };

  return (
    <div className="container modern-container">
      <div className="modern-card">
        <h2 className="form-heading">Log in</h2>
        <Form onSubmit={handleSubmit}>
          <FormGroup className="form-group">
            <Label htmlFor="username">Username</Label>
            <Input
              type="text"
              className="form-control"
              id="username"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </FormGroup>
          <FormGroup className="form-group">
            <Label htmlFor="password">Password</Label>
            <Input
              type="password"
              className="form-control"
              id="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </FormGroup>
          {showError && <Alert variant="danger">{error}</Alert>}
          <Button type="submit" className="btn btn-lg btn-primary btn-block">
            Log In
          </Button>
          <h4 className="text-center">
            <a href="/" className="modern-link">Create an account</a>
          </h4>
        </Form>
      </div>
    </div>
  );
};

export default Login;