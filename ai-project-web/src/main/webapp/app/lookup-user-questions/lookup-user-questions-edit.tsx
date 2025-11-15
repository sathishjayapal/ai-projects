import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { LookupUserQuestionsDTO } from 'app/lookup-user-questions/lookup-user-questions-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    questionAsked: yup.string().emptyToNull().max(255),
    lookupusername: yup.number().integer().emptyToNull()
  });
}

export default function LookupUserQuestionsEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('lookupUserQuestions.edit.headline'));

  const navigate = useNavigate();
  const [lookupusernameValues, setLookupusernameValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const lookupusernameValuesResponse = await axios.get('/api/lookupUserQuestionss/lookupusernameValues');
      setLookupusernameValues(lookupusernameValuesResponse.data);
      const data = (await axios.get('/api/lookupUserQuestionss/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateLookupUserQuestions = async (data: LookupUserQuestionsDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/lookupUserQuestionss/' + currentId, data);
      navigate('/lookupUserQuestionss', {
            state: {
              msgSuccess: t('lookupUserQuestions.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('lookupUserQuestions.edit.headline')}</h1>
      <div>
        <Link to="/lookupUserQuestionss" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('lookupUserQuestions.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateLookupUserQuestions)} noValidate>
      <InputRow useFormResult={useFormResult} object="lookupUserQuestions" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="lookupUserQuestions" field="questionAsked" />
      <InputRow useFormResult={useFormResult} object="lookupUserQuestions" field="lookupusername" type="select" options={lookupusernameValues} />
      <input type="submit" value={t('lookupUserQuestions.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 cursor-pointer mt-6" />
    </form>
  </>);
}
