import axios from 'axios';
import React, { useState, useEffect } from 'react';
import { Document } from './Document'; // Assuming Document component exists

const fetchDocuments = async () => {
  try {
    const response = await axios.get('/api/docs');
    return response.data;
  } catch (err) {
    throw err;
  }
};

const Welcome = () => {
  const [documents, setDocuments] = useState<Document[]>([]);

  useEffect(() => {
    const fetchAndSetDocuments = async () => {
      const documentsData = await fetchDocuments();
      setDocuments(documentsData);
    };

    fetchAndSetDocuments();
  }, []);

  return (
    <div>
      <h1>Welcome to the Document Library</h1>
      <ul>
        {documents.map(doc => (
          <li key={doc.id}>
            <Document document={doc} />
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Welcome;