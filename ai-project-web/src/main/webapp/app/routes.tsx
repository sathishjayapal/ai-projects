import React from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router';
import App from "./app";
import Home from './home/home';
import LookupUserQuestionsList from './lookup-user-questions/lookup-user-questions-list';
import LookupUserQuestionsAdd from './lookup-user-questions/lookup-user-questions-add';
import LookupUserQuestionsEdit from './lookup-user-questions/lookup-user-questions-edit';
import DocDBDataList from './doc-d-b-data/doc-d-b-data-list';
import DocDBDataAdd from './doc-d-b-data/doc-d-b-data-add';
import DocDBDataEdit from './doc-d-b-data/doc-d-b-data-edit';
import Error from './error/error';


export default function AppRoutes() {
  const router = createBrowserRouter([
    {
      element: <App />,
      children: [
        { path: '', element: <Home /> },
        { path: 'lookupUserQuestionss', element: <LookupUserQuestionsList /> },
        { path: 'lookupUserQuestionss/add', element: <LookupUserQuestionsAdd /> },
        { path: 'lookupUserQuestionss/edit/:id', element: <LookupUserQuestionsEdit /> },
        { path: 'docDBDatas', element: <DocDBDataList /> },
        { path: 'docDBDatas/add', element: <DocDBDataAdd /> },
        { path: 'docDBDatas/edit/:id', element: <DocDBDataEdit /> },
        { path: 'error', element: <Error /> },
        { path: '*', element: <Error /> }
      ]
    }
  ]);

  return (
    <RouterProvider router={router} />
  );
}
