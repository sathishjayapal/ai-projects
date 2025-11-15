import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useSearchParams } from 'react-router';
import { handleServerError, getListParams } from 'app/common/utils';
import { DocDBDataDTO } from 'app/doc-d-b-data/doc-d-b-data-model';
import { Pagination } from 'app/common/list-helper/pagination';
import { ListModel } from 'app/common/hateoas';
import axios from 'axios';
import SearchFilter from 'app/common/list-helper/search-filter';
import Sorting from 'app/common/list-helper/sorting';
import useDocumentTitle from 'app/common/use-document-title';


export default function DocDBDataList() {
  const { t } = useTranslation();
  useDocumentTitle(t('docDBData.list.headline'));

  const [docDBDatas, setDocDBDatas] = useState<ListModel<DocDBDataDTO>|undefined>(undefined);
  const navigate = useNavigate();
  const [searchParams, ] = useSearchParams();
  const listParams = getListParams();
  const sortOptions = {
    'id,ASC': t('docDBData.list.sort.id,ASC'), 
    'name,ASC': t('docDBData.list.sort.name,ASC')
  };

  const getAllDocDBDatas = async () => {
    try {
      const response = await axios.get('/api/docDBDatas?' + listParams);
      setDocDBDatas(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/docDBDatas/' + id);
      navigate('/docDBDatas', {
            state: {
              msgInfo: t('docDBData.delete.success')
            }
          });
      getAllDocDBDatas();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllDocDBDatas();
  }, [searchParams]);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('docDBData.list.headline')}</h1>
      <div>
        <Link to="/docDBDatas/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('docDBData.list.createNew')}</Link>
      </div>
    </div>
    {((docDBDatas?._embedded && docDBDatas?.page?.totalElements !== 0) || searchParams.get('filter')) && (
    <div className="flex flex-wrap justify-between">
      <SearchFilter placeholder={t('docDBData.list.filter')} />
      <Sorting sortOptions={sortOptions} />
    </div>
    )}
    {!docDBDatas?._embedded || docDBDatas?.page?.totalElements === 0 ? (
    <div>{t('docDBData.list.empty')}</div>
    ) : (<>
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('docDBData.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('docDBData.name.label')}</th>
            <th scope="col" className="text-left p-2">{t('docDBData.username.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {docDBDatas?._embedded?.['docDBDataDTOList']?.map((docDBData) => (
          <tr key={docDBData.id} className="odd:bg-gray-100">
            <td className="p-2">{docDBData.id}</td>
            <td className="p-2">{docDBData.name}</td>
            <td className="p-2">{docDBData.username}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/docDBDatas/edit/' + docDBData.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('docDBData.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(docDBData.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm cursor-pointer">{t('docDBData.list.delete')}</button>
              </div>
            </td>
          </tr>
          ))}
        </tbody>
      </table>
    </div>
    <Pagination page={docDBDatas?.page} />
    </>)}
  </>);
}
