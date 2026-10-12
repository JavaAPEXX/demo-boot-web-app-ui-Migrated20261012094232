import React from 'react';

export default {
  title: 'Welcome Page',
  component: Welcome,
};

const Template = (args) => <Welcome {...args} />;

export const Default = Template.bind({});
Default.args = {};

export const Loading = Template.bind({});
Loading.args = {
  loading: true,
};

export const Error = Template.bind({});
Error.args = {
  error: 'Failed to fetch documents',
};

export const Documents = Template.bind({});
Documents.args = {
  docsList: [
    { id: 1, title: 'Document Title', description: 'Document Description', link: 'https://example.com' },
  ],
};