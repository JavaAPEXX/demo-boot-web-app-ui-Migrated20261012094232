import React from 'react';
import { render, screen } from '@testing-library/react';

interface WelcomeProps {
  loading: boolean;
  error: boolean;
  documents: string[];
}

const Welcome: React.FC<WelcomeProps> = ({ loading, error, documents }) => {
  return (
    <div>
      {loading && <p>Loading...</p>}
      {error && <p>Failed to fetch documents</p>}
      {!loading && !error && (
        <div>
          <h1>Document List</h1>
          <ul>
            {documents.map((document) => (
              <li key={document}>{document}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default Welcome;