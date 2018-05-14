import { Component, OnInit, Inject } from '@angular/core';
import { Niggle } from '../../niggle.model';
import { NiggleService } from '../../niggle.service';

import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { MAT_DIALOG_DATA } from '@angular/material';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { MatDialogRef, MatSnackBar } from '@angular/material';

@Component({
  selector: 'jhi-delete-dialog-quattra',
  templateUrl: './delete-dialog-quattra.component.html',
  styleUrls: ['./delete-dialog-quattra.component.css']
})
export class DeleteDialogQuattraComponent implements OnInit {

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
      const snackBarRef = this.snackBar.open('Niggle deleted', 'Dismiss', {
        duration: 3000
      });
    });
  }
}
