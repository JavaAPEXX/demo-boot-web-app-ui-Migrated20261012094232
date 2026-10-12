import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Button, Form, FormGroup, Label, Input, Alert } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

interface UserForm {
  username: string;
  password: string;
  passwordConfirm: string;
}

const Registration: React.FC = () => {
  const [userForm, setUserForm] = useState<UserForm>({
    username: '',
    password: '',
    passwordConfirm: '',
  });
  const [errors, setErrors] = useState({ username: '', password: '', passwordConfirm: '' });
  const [status, setStatus] = useState({ error: false, message: '' });
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setUserForm({ ...userForm, [name]: value });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const { username, password, passwordConfirm } = userForm;

    if (password !== passwordConfirm) {
      setErrors({ username: '', password: '', passwordConfirm: 'Passwords do not match' });
      return;
    }

    try {
      // Simulate backend call
      const response = await fetch('/registration', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userForm),
      });

      if (response.ok) {
        navigate('/welcome');
      } else {
        const errorData = await response.json();
        setErrors({ username: '', password: '', passwordConfirm: errorData.message });
      }
    } catch (error) {
      console.error('Registration failed:', error);
      setErrors({ username: '', password: '', passwordConfirm: 'An error occurred' });
    }
  };

  return (
    <div className="container">
      <Form onSubmit={handleSubmit}>
        <h2 className="form-signin-heading">Create your account</h2>
        <FormGroup className={errors.username ? 'has-error' : ''}>
          <Label htmlFor="username">Username</Label>
          <Input
            type="text"
            name="username"
            value={userForm.username}
            onChange={handleChange}
            placeholder="Username"
            autoFocus
          />
          {errors.username && <Alert variant="danger">{errors.username}</Alert>}
        </FormGroup>

        <FormGroup className={errors.password ? 'has-error' : ''}>
          <Label htmlFor="password">Password</Label>
          <Input
            type="password"
            name="password"
            value={userForm.password}
            onChange={handleChange}
            placeholder="Password"
          />
          {errors.password && <Alert variant="danger">{errors.password}</Alert>}
        </FormGroup>

        <FormGroup className={errors.passwordConfirm ? 'has-error' : ''}>
          <Label htmlFor="passwordConfirm">Confirm your password</Label>
          <Input
            type="password"
            name="passwordConfirm"
            value={userForm.passwordConfirm}
            onChange={handleChange}
            placeholder="Confirm your password"
          />
          {errors.passwordConfirm && <Alert variant="danger">{errors.passwordConfirm}</Alert>}
        </FormGroup>

        <Button variant="primary" type="submit">Submit</Button>
      </Form>
    </div>
  );
};

export default Registration;