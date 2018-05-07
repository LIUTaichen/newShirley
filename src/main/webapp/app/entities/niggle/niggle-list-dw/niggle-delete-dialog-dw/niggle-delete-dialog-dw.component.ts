import { Component, OnInit, Inject } from '@angular/core';
import { Niggle } from '../../niggle.model';
import { NiggleService } from '../../niggle.service';

import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { MAT_DIALOG_DATA } from '@angular/material';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'jhi-niggle-delete-dialog-dw',
  templateUrl: './niggle-delete-dialog-dw.component.html',
  styleUrls: ['./niggle-delete-dialog-dw.component.css']
})
export class NiggleDeleteDialogDwComponent implements OnInit {

  id: any;
  constructor(private niggleService: NiggleService,
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
    });
  }
}
