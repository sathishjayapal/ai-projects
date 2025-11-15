import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { DocDBDataDTO } from 'app/doc-d-b-data/doc-d-b-data-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    name: yup.string().emptyToNull().max(255).required(),
    fileName: yup.object().emptyToNull().json(),
    username: yup.number().integer().emptyToNull().required()
  });
}

export default function DocDBDataAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('docDBData.add.headline'));

  const navigate = useNavigate();
  const [usernameValues, setUsernameValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      DOC_DBDATA_NAME_UNIQUE: t('exists.docDBData.name')
    };
    return messages[key];
  };

  const prepareRelations = async () => {
    try {
      const usernameValuesResponse = await axios.get('/api/docDBDatas/usernameValues');
      setUsernameValues(usernameValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createDocDBData = async (data: DocDBDataDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/docDBDatas', data);
      navigate('/docDBDatas', {
            state: {
              msgSuccess: t('docDBData.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('docDBData.add.headline')}</h1>
      <div>
        <Link to="/docDBDatas" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('docDBData.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createDocDBData)} noValidate>
      <InputRow useFormResult={useFormResult} object="docDBData" field="name" required={true} />
      <InputRow useFormResult={useFormResult} object="docDBData" field="fileName" type="file" />
      <InputRow useFormResult={useFormResult} object="docDBData" field="username" required={true} type="select" options={usernameValues} />
      <input type="submit" value={t('docDBData.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 cursor-pointer mt-6" />
    </form>
  </>);
}
