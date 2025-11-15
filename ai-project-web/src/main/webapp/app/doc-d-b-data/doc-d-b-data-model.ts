import { Resource } from 'app/common/hateoas';


export class DocDBDataDTO extends Resource  {

  constructor(data:Partial<DocDBDataDTO>) {
    super();
    Object.assign(this, data);
    if (data.fileName) {
      this.fileName = JSON.parse(data.fileName);
    }
  }

  id?: number|null;
  name?: string|null;
  fileName?: any|null;
  username?: number|null;

}
