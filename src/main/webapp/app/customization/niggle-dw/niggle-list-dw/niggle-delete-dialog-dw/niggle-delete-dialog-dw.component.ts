import { Component, OnInit, Inject } from '@angular/core';
import { NiggleService } from '../../../../entities/niggle/niggle.service';

import { MAT_DIALOG_DATA } from '@angular/material';
import { JhiEventManager } from 'ng-jhipster';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'jhi-niggle-delete-dialog-dw',
  templateUrl: './niggle-delete-dialog-dw.component.html',
  styleUrls: ['./niggle-delete-dialog-dw.component.css']
})
export class NiggleDeleteDialogDwComponent implements OnInit {

  id: any;
  constructor(
    public snackBar: MatSnackBar,
    private niggleService: NiggleService,
    private eventManager: JhiEventManager,
    @Inject(MAT_DIALOG_DATA) public data: any) {
      this.id = data.id;
    }
  ngOnInit() {
  }

  confirmDelete() {
    console.log('deleting');
    this.niggleService.delete(this.id).subscribe((response) => {
      this.eventManager.broadcast({
        name: 'niggleListModification',
        content: 'Deleted an niggle'
      });
      this.snackBar.open('Niggle deleted', 'Dismiss', {
        duration: 3000
      });
    });
  }
}
