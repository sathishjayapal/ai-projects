import { Resource } from 'app/common/hateoas';


export class LookupUserQuestionsDTO extends Resource  {

  constructor(data:Partial<LookupUserQuestionsDTO>) {
    super();
    Object.assign(this, data);
  }

  id?: number|null;
  questionAsked?: string|null;
  lookupusername?: number|null;

}
