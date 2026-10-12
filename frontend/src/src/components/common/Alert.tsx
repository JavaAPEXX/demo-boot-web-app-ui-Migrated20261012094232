import React from 'react';

const Alert = ({ type, children }) => {
  const alertType = type === 'danger' ? 'alert-danger' : 'alert-success';
  return (
    <div className={`alert ${alertType} alert-dismissible fade show`}>
      {children}
      <button type="button" className="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
  );
};

export default Alert;