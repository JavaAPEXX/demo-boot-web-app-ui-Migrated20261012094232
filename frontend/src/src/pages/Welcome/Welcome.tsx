import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Modal, Alert } from 'components/common';
import { Header, Footer } from 'components/layout';
import { fetchDocuments } from 'services/docService';

const Welcome = () => {
  const [docsList, setDocsList] = useState<Document[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchDocumentsData = async () => {
      try {
        const response = await fetchDocuments();
        setDocsList(response);
        setLoading(false);
      } catch (err) {
        setError('Failed to fetch documents');
        setLoading(false);
      }
    };

    fetchDocumentsData();
  }, []);

  return (
    <div className="modern-container">
      <Header />
      <div className="modern-card">
        <h3>Document List</h3>
        {loading && <p>Loading...</p>}
        {error && <Alert type="danger">{error}</Alert>}
        {docsList.length > 0 ? (
          <table className="table table-striped custab modern-table">
            <thead>
              <tr>
                <th>Title</th>
                <th>Description</th>
                <th className="text-center">Action</th>
              </tr>
            </thead>
            <tbody>
              {docsList.map((doc) => (
                <tr key={doc.id}>
                  <td>{doc.title}</td>
                  <td>{doc.description}</td>
                  <td className="text-center">
                    <Link to={`/download/${doc.id}`} download>
                      <button className="btn btn-info btn-xs modern-button">
                        <span className="glyphicon glyphicon-download"></span> Download
                      </button>
                    </Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <h5>No document to display</h5>
        )}
      </div>
      <Footer />
    </div>
  );
};

export default Welcome;