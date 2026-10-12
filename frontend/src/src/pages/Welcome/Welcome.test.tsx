import React from 'react';
import { render, screen } from '@testing-library/react';

function Welcome() {
  return (
    <div>
      <h1>Document List</h1>
    </div>
  );
}

export default Welcome;

test('renders welcome page', () => {
  render(<Welcome />);
  const title = screen.getByText('Document List');
  expect(title).toBeInTheDocument();
});