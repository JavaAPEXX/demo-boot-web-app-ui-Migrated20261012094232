import React from 'react';
import { render } from '@testing-library/react';

interface WelcomeProps {
  isLoading: boolean;
  hasError: boolean;
  documents: string[];
}

const Welcome: React.FC<WelcomeProps> = ({ isLoading, hasError, documents }) => {
  return (
    <div>
      {isLoading && <p>Loading...</p>}
      {hasError && <p>Failed to fetch documents</p>}
      {documents.length > 0 && (
        <div>
          <h1>Document List</h1>
          <ul>
            {documents.map((doc) => (
              <li key={doc}>{doc}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default Welcome;