import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useSearchParams } from 'react-router';
import { handleServerError, getListParams } from 'app/common/utils';
import { LookupUserQuestionsDTO } from 'app/lookup-user-questions/lookup-user-questions-model';
import { Pagination } from 'app/common/list-helper/pagination';
import { ListModel } from 'app/common/hateoas';
import axios from 'axios';
import SearchFilter from 'app/common/list-helper/search-filter';
import Sorting from 'app/common/list-helper/sorting';
import useDocumentTitle from 'app/common/use-document-title';


export default function LookupUserQuestionsList() {
  const { t } = useTranslation();
  useDocumentTitle(t('lookupUserQuestions.list.headline'));

  const [lookupUserQuestionses, setLookupUserQuestionses] = useState<ListModel<LookupUserQuestionsDTO>|undefined>(undefined);
  const navigate = useNavigate();
  const [searchParams, ] = useSearchParams();
  const listParams = getListParams();
  const sortOptions = {
    'id,ASC': t('lookupUserQuestions.list.sort.id,ASC'), 
    'questionAsked,ASC': t('lookupUserQuestions.list.sort.questionAsked,ASC')
  };

  const getAllLookupUserQuestionses = async () => {
    try {
      const response = await axios.get('/api/lookupUserQuestionss?' + listParams);
      setLookupUserQuestionses(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/lookupUserQuestionss/' + id);
      navigate('/lookupUserQuestionss', {
            state: {
              msgInfo: t('lookupUserQuestions.delete.success')
            }
          });
      getAllLookupUserQuestionses();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllLookupUserQuestionses();
  }, [searchParams]);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('lookupUserQuestions.list.headline')}</h1>
      <div>
        <Link to="/lookupUserQuestionss/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('lookupUserQuestions.list.createNew')}</Link>
      </div>
    </div>
    {((lookupUserQuestionses?._embedded && lookupUserQuestionses?.page?.totalElements !== 0) || searchParams.get('filter')) && (
    <div className="flex flex-wrap justify-between">
      <SearchFilter placeholder={t('lookupUserQuestions.list.filter')} />
      <Sorting sortOptions={sortOptions} />
    </div>
    )}
    {!lookupUserQuestionses?._embedded || lookupUserQuestionses?.page?.totalElements === 0 ? (
    <div>{t('lookupUserQuestions.list.empty')}</div>
    ) : (<>
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('lookupUserQuestions.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('lookupUserQuestions.questionAsked.label')}</th>
            <th scope="col" className="text-left p-2">{t('lookupUserQuestions.lookupusername.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {lookupUserQuestionses?._embedded?.['lookupUserQuestionsDTOList']?.map((lookupUserQuestions) => (
          <tr key={lookupUserQuestions.id} className="odd:bg-gray-100">
            <td className="p-2">{lookupUserQuestions.id}</td>
            <td className="p-2">{lookupUserQuestions.questionAsked}</td>
            <td className="p-2">{lookupUserQuestions.lookupusername}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/lookupUserQuestionss/edit/' + lookupUserQuestions.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('lookupUserQuestions.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(lookupUserQuestions.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm cursor-pointer">{t('lookupUserQuestions.list.delete')}</button>
              </div>
            </td>
          </tr>
          ))}
        </tbody>
      </table>
    </div>
    <Pagination page={lookupUserQuestionses?.page} />
    </>)}
  </>);
}
